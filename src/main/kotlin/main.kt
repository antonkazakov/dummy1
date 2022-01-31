import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.treewalk.AbstractTreeIterator
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import java.io.File


fun main(args: Array<String>) {
    val repoDir = File(".")
    val git = Git.open(repoDir)

    val oldTreeParser = prepareTreeParser(git.repository, "refs/heads/testbranch")
    val newTreeParser = prepareTreeParser(git.repository, "refs/heads/master")

    val diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser).call()
    for (entry in diff) {
        println("Entry: $entry")
    }
}

private fun prepareTreeParser(repository: Repository, ref: String): AbstractTreeIterator {
    val head = repository.exactRef(ref)
    RevWalk(repository).let { walk ->
        val commit: RevCommit = walk.parseCommit(head.objectId)
        val tree: RevTree = walk.parseTree(commit.tree.id)
        val treeParser = CanonicalTreeParser()
        repository.newObjectReader().let { reader -> treeParser.reset(reader, tree.id) }
        walk.dispose()
        return treeParser
    }
}