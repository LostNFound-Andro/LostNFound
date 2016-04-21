all: refman.pdf

pdf: refman.pdf

refman.pdf: clean refman.tex
	pdflatex refman
<<<<<<< HEAD
	makeindex refman.idx
	pdflatex refman
	latex_count=8 ; \
=======
	makeindex refman.tex
	pdflatex refman
	latex_count=5 ; \
>>>>>>> 0bbb2bc5f31f033d772ef4573f66c50564006509
	while egrep -s 'Rerun (LaTeX|to get cross-references right)' refman.log && [ $$latex_count -gt 0 ] ;\
	    do \
	      echo "Rerunning latex...." ;\
	      pdflatex refman ;\
	      latex_count=`expr $$latex_count - 1` ;\
	    done
<<<<<<< HEAD
	makeindex refman.idx
	pdflatex refman
=======
>>>>>>> 0bbb2bc5f31f033d772ef4573f66c50564006509


clean:
	rm -f *.ps *.dvi *.aux *.toc *.idx *.ind *.ilg *.log *.out *.brf *.blg *.bbl refman.pdf
