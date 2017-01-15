#!/bin/bash
pandoc --variable urlcolor=blue -V geometry:margin=1.3in -o report.tex POSTMORTEM.md --self-contained --number-section
