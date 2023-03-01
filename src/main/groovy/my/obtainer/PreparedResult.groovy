package my.obtainer

class PreparedResult extends JsonWriter {

    String externalTaskId

    Integer amountOfRepos

    Boolean isFinished

    PreparedResult(String externalTaskId, Integer amountOfRepos, Boolean isFinished) {
        this.externalTaskId = externalTaskId
        this.amountOfRepos = amountOfRepos
        this.isFinished = isFinished
    }
}
