package my.obtainer

import my.common.JsonWriter

class PreparedResult extends JsonWriter {

    String externalTaskId

    Integer amountOfRepos

    String isFinished

    PreparedResult(String externalTaskId, Integer amountOfRepos, String isFinished) {
        this.externalTaskId = externalTaskId
        this.amountOfRepos = amountOfRepos
        this.isFinished = isFinished
    }
}
