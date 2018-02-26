## Getting Started
This is mostly a summary, plus a few additions, of the very nice work done by Nick Tyler (github.com/tylern4).

Pull Nick's image of clas6db:
```
docker pull tylern4/clas6db
```
Clone `docker-clas6` (forked from Nick);
```
git clone https://github.com/naharrison/docker-clas6.git
cd docker-clas6/clas6
```
Download requirements:
```
svn co --username nathanh https://jlabsvn.jlab.org/svnroot/clas/trunk/ clas-software
scp -r nathanh@ftp.jlab.org:/home/nathanh/spgen . # (originally from Stepan)
scp -r nathanh@ftp.jlab.org:/group/clas/builds/centos7/trunk/simulation/generators/clasDIS .
# also maybe try these versions of clasDIS:
# /group/clas/builds/centos65/trunk/simulation/generators/clasDIS <-- confirmed works
# /home/nathanh/clasDIS
scp nathanh@ftp.jlab.org:"~/DISstuffOS62/ffread.in" .
```
Add the following lines to Dockerfile just before the final `ENTRYPOINT` line:
```
RUN yum install -y vim-enhanced
RUN alias ls ls --color
COPY spgen ./spgen
COPY clasDIS ./clasDIS
```
Build the image:
```
docker build -t clas6:test .
```
Run the image with:
```
docker run -v`pwd`:/root/data -it clas6:test
```
The `-v` option mounts a volume (your current working directory) to the running docker image (at /root/data/).

## spgen and clasDIS
Annoyingly, different parts of clas software need the same variable set to different values.

For spgen, create and run a file `myMake.sh`:
```
#!/bin/bash -f

export CERN=/usr/local/cernlib/x86_64_rhel6/
make clean
make

echo ""
echo executable is "spgen.exe", parameters for generating a particle must be defined in "spgen_init.dat". Explanations are in the same f
ile.
echo ""
echo 'pi0 pid is 111, decay codes are 671 (Dalitz decay) and 670 (2 photons)'
echo ""
echo see lund_upd.dat for some more stuff
echo ""
```

For clasDIS, create and run a file `myScons.sh`:
```
#!/bin/bash -f

export PREFIX=/clas-software
export CERN=/usr/local/cernlib/x86_64_rhel6/2005
export PYTHONPATH=$PREFIX

scons
```

Used to run clasdis with: `clasdis --trig 20000 --datf --outform 2 --beam 5498 --zpos -250 --zwidth 25 --t 5 60 --parl3 0.7 --lst15 145 --path ./CDOUT --parj33 0.3` but now some of those options are missing. Now do: `build/bin/clasDIS --trig 20000 --datf --outform 2 --beam 5498 --zpos -250 --zwidth 25 --t 5 60` and manually update the output path in line 205 of clasDIS.F (note filename will have a bunch of annoying spaces in front of it). Need to talk to Harut about these changes...

## More stuff to try
* In the same `docker-clas6` repository, see `docker-clas6/clasdb` for documentation on running a docker container with clasdb.
* Get gpp_2008
* Rebuild clas6 image with tcsh (anything else??)
