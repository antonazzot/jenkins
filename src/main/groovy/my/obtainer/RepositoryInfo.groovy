package my.obtainer

class RepositoryInfo {
    String owner
    String url
    String branch
    String sha

    RepositoryInfo(String owner, String url, String branch, String sha) {
        this.url = url
        this.branch = branch
        this.owner = owner
        this.sha = sha
    }
}
