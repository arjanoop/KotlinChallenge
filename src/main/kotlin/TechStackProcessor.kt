import java.util.regex.Pattern

class TechStackProcessor {
    fun processTechStack(techMap: MutableMap<String, MutableSet<String>>) {
        val emptyStackUrl: MutableSet<String> = HashSet()
        for (url in techMap.keys) {
            simplifyTechnologies(techMap, url, getDirectoryParent(url))
            if (techMap[url]?.isEmpty() == true) {
                emptyStackUrl.add(url)
            }
        }
        for (key in emptyStackUrl) {
            techMap.remove(key)
        }
    }

    private fun getDirectoryParent(url: String): String? {
        val lastIndex = url.lastIndexOf('/')
        return if (lastIndex == -1) {
            null
        } else {
            url.substring(0, lastIndex)
        }
    }

    private fun isParentDirectory(url: String): Boolean {
        val regex = "^(https?://[^/]+/.+?/)"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(url)
        return if (matcher.find()) {
            matcher.group(1) == url
        } else {
            false
        }
    }

    private fun getParentTech(techMap: MutableMap<String, MutableSet<String>>, immediateParent: String): Set<String>? {
        return if (techMap.containsKey(immediateParent)) {
            techMap[immediateParent]
        } else if (techMap.containsKey("$immediateParent/")) {
            techMap["$immediateParent/"]
        } else {
            null
        }
    }

    private fun simplifyTechnologies(
        techMap: MutableMap<String, MutableSet<String>>,
        currentUrl: String,
        immediateParent: String?
    ) {
        if (immediateParent == null || isParentDirectory(currentUrl)) {
            return
        }
        immediateParent.let { parentUrl ->
            val currentTech = techMap[currentUrl]!!
            val parentTech: Set<String>? = getParentTech(techMap, parentUrl)
            if (parentTech != null) {
                currentTech.removeAll(parentTech)
            }
            if (currentTech.size > 0) {
                simplifyTechnologies(techMap, currentUrl, getDirectoryParent(parentUrl))
            } else {
                return
            }
        }
    }
}