fun main(args: Array<String>) {
    // Sample input data
    val techMap: MutableMap<String, MutableSet<String>> = HashMap()
    techMap["https://upb.de/site/drupal"] = HashSet(mutableListOf("Drupal", "Apache", "PHP5", "RedHat"))
    techMap["https://upb.de/site/drupal/a"] = HashSet(mutableListOf("Drupal", "Apache", "PHP5", "RedHat"))
    techMap["https://upb.de/site/drupal/a/b"] = HashSet(mutableListOf("Drupal", "Apache", "PHP5", "RedHat"))
    techMap["https://upb.de/site/drupal/a/c"] = HashSet(mutableListOf("Drupal", "Apache", "PHP5", "RedHat"))
    techMap["https://upb.de/site/drupal/a/d/e"] = HashSet(mutableListOf("Drupal", "Apache", "PHP5", "RedHat"))
    techMap["https://upb.de/site/drupal/f"] = HashSet(mutableListOf("Drupal", "Apache", "PHP5", "RedHat"))
    techMap["https://upb.de/site/drupal/g"] = HashSet(mutableListOf("Drupal", "Apache", "PHP5", "RedHat", "AngularJS"))
    techMap["https://upb.de/site/jml"] = HashSet(mutableListOf("Joomla", "Apache", "PHP4", "AngularJS"))
    techMap["https://upb.de/site/jml/pdfs"] = HashSet(mutableListOf("PDF generator"))
    techMap["https://upb.de/site/"] = HashSet(mutableListOf("Apache", "PHP5"))

    //print input
    println("Input:");
    printTechnologies(techMap);

    //processing
    val processor = TechStackProcessor();
    processor.processTechStack(techMap);

    //print outputSS
    println("Output:");
    printTechnologies(techMap);
}

fun printTechnologies(map: Map<String, Set<String?>?>) {
    for ((key, value) in map) {
        println(key + " -> " + java.lang.String.join(", ", value))
    }
}