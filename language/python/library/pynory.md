# pynori
```python
import pynori

from pynori.korean_analyzer import KoreanAnalyzer

analyzer_basic = KoreanAnalyzer(decompound_mode='DISCARD',
                                infl_decompound_mode='DISCARD',
                                discard_punctuation=True,
                                output_unknown_unigrams=False,
                                pos_filter=False,
                                synonym_filter=True, mode_synonym='NORM')
```