FROM gcc:4.9
RUN apt-get install g++ 
CMD root
RUN g++ -o TCanvas test.c
COPY . /TCanvas
WORKDIR /TCanvas

CMD ["./TCanvas"]
