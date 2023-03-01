package my.common.structure.parser

import my.obtainer.RepositoryInfo

interface RepositoryInfoParser {
    List<RepositoryInfo> parse (String input)
}
