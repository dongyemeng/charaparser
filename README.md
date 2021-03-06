<img src="http://biosemantics.github.io/charaparser/images/CP_Logo.jpg">
===========
CharaParser is a NLP tool which processes morphological descriptions of the biodiversity domain.
The output of CharaParser is a structured description of structures, their characters and relations between structures
in XML format.

Relevant Publications 
---------------------

1. Cui, H., Boufford, D., & Selden, P. (2010). Semantic annotation of biosystematics literature without training examples. Journal of American Society of Information Science and Technology. 61 (3): 522-542.http://onlinelibrary.wiley.com/doi/10.1002/asi.21246/full

2. Cui, H. (2012). CharaParser for fine-grained semantic annotation of organism morphological descriptions. Journal of American Society of Information Science and Technology. 63(4) DOI: 10.1002/asi.22618 http://onlinelibrary.wiley.com/doi/10.1002/asi.22618/pdf

If you use CharaParser in your research/work, please cite the above publications.

License
-------

   Copyright 2013 CharaParser Authors

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.


Project Page
----------
More information is available on the <a href="http://etc-project.org/">project page</a>.

Contribution
----------
If you want to contribute, the source is built using Maven and AspectJ.
In Eclipse you can therefore use:
* m2e - Maven Integration for Eclipse (e.g. for Juno version: http://download.eclipse.org/releases/juno)
* AspectJ Development Tools (http://download.eclipse.org/tools/ajdt/42/update)
* Maven Integration for AJDT (http://dist.springsource.org/release/AJDT/configurator/)

and configure your Eclipse project to be a Maven and AspectJ Project.

Sources are built for Java compiler compliance level 1.7.

Software Dependencies
----------
In its current version, CharaParser dependes on the following additional Software.
* MySQL
* Perl
* WordNet

JavaDoc
----------
The JavaDoc has been generated with <a href="https://code.google.com/p/apiviz/">APIviz</a> and can be found 
<a href="http://biosemantics.github.com/charaparser/javadoc">here</a>.

Class Diagrams
----------
The following class diagrams give an overview on CharapParser's architecture:

<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/01IRun.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/01IRun.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/02MarkupEvaluationRun.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/02MarkupEvaluationRun.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/03IMarkupCreator.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/03IMarkupCreator.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/04MarkupDescriptionTreatmentTransformer.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/04MarkupDescriptionTreatmentTransformer.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/05ITerminologyLearner.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/05ITerminologyLearner.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/06DescriptionExtractorRun.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/06DescriptionExtractorRun.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/07SentenceChunkerRun.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/07SentenceChunkerRun.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/08IPOSTagger.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/08IPOSTagger.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/09IParser.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/09IParser.png">
</a>
<br>
<a href="http://biosemantics.github.com/charaparser/images/classDiagrams/10IDescriptionExtractor.png">
<img src="http://biosemantics.github.com/charaparser/images/classDiagrams/10IDescriptionExtractor.png">
</a>
<br>
