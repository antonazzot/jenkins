package my.structure

import com.cloudbees.groovy.cps.NonCPS

class RepositoryInfoParserImpl implements RepositoryInfoParser {

    private final String OWNER_DELIMETR = "&"
    private final String INSIDE_DELIMETR = "#";


    @Override
    @NonCPS
    List<RepositoryInfo> parse(String input) {
        println("inside parse:   " )
        List<RepositoryInfo> repositoryInfoList = new ArrayList<>();
        if (input.blank) {
            return Collections.EMPTY_LIST
        }
        for (owner in input.split(OWNER_DELIMETR)) {
            def split = owner.split(INSIDE_DELIMETR)
            if (!split.empty) {
                continue
            }
            repositoryInfoList.add(new RepositoryInfo(split[0], split[1], split[2], split[3]))
        }
        println("Parse finished")
        return repositoryInfoList;
    }

    @NonCPS
    RepositoryInfo parseSingle(String input) {
        println("inside single parse:   " + input)

        def split = input.split(INSIDE_DELIMETR)

        return new RepositoryInfo(split[0], split[1], split[2], split[3])
    }
}
