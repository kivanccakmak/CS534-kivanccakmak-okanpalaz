#!/bin/bash
cat POSTMORTEM.md UMLS.md > report.md && pandoc --variable urlcolor=blue -V geometry:margin=1.3in -o report.tex report.md --self-contained && rm report.md
