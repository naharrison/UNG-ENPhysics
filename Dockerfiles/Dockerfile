FROM fedora:latest

WORKDIR /mydocker

RUN yum -y install which tcsh vim-enhanced root.x86_64 xorg-x11-apps.x86_64

COPY root-test.C .

ENTRYPOINT ["/bin/tcsh"]
