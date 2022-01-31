import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.util.FileUtils
import java.io.File


fun main(args: Array<String>) {
    val repoDir = File(".")

    val builder = FileRepositoryBuilder()
    val git = Git.open(repoDir)

    git.add().addFilepattern(".").call()

    git.commit().setMessage("fdsf").call()

}