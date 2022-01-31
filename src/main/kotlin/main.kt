import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.util.FileUtils
import java.io.File


fun main(args: Array<String>) {
    val repoDir = File("..")

    val builder = FileRepositoryBuilder()
    val repo = builder.setGitDir(repoDir)
        .readEnvironment()
        .findGitDir()
        .build()

    repo.let { repository ->
        val head = repository.exactRef("refs/heads/master")
        println("Ref of refs/heads/master: $head")
    }
}