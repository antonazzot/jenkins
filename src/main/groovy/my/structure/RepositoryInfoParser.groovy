package my.structure

import my.structure.RepositoryInfo

interface RepositoryInfoParser {
    List<RepositoryInfo> parse (String input)
}
