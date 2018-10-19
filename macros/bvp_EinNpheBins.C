{

const int nEinBins = 5;
float einMin = 0.0;
float einMax = 0.9;
float einBW = (einMax - einMin)/nEinBins;
const int nNpheBins = 5;
float npheMin = 0.0;
float npheMax = 350.0;
float npheBW = (npheMax - npheMin)/nNpheBins;

TH2F *bvp_pos[nEinBins][nNpheBins];
TH2F *bvp_pi[nEinBins][nNpheBins];
TH2F *bvp_k[nEinBins][nNpheBins];
TH2F *bvp_prot[nEinBins][nNpheBins];
TH2F *bvp[nEinBins][nNpheBins];

for(int e = 0; e < nEinBins; e++) {
  for(int n = 0; n < nNpheBins; n++) {

    bvp_pos[e][n] = new TH2F(Form("bvp_pos_e%i_n%i", e, n), Form("bvp_pos_e%i_n%i", e, n), 200, 0, 5, 200, 0.5, 1.2);
    bvp_pi[e][n] = new TH2F(Form("bvp_pi_e%i_n%i", e, n), Form("bvp_pi_e%i_n%i", e, n), 200, 0, 5, 200, 0.5, 1.2);
    bvp_k[e][n] = new TH2F(Form("bvp_k_e%i_n%i", e, n), Form("bvp_k_e%i_n%i", e, n), 200, 0, 5, 200, 0.5, 1.2);
    bvp_prot[e][n] = new TH2F(Form("bvp_prot_e%i_n%i", e, n), Form("bvp_prot_e%i_n%i", e, n), 200, 0, 5, 200, 0.5, 1.2);
    bvp[e][n] = new TH2F(Form("bvp_e%i_n%i", e, n), Form("bvp_e%i_n%i", e, n), 200, 0, 5, 200, 0.5, 1.2);

}}

ifstream infile("/Users/naharrison/master/data-samples/e1f/Pid-Data/tmp-649951.txt");
for(int k = 0; k < 649951; k++) {
  int pid;
  float p, th, beta, nphe, ein, eout;
  infile >> pid >> p >> th >> beta >> nphe >> ein >> eout;

  int einBin = (ein - einMin)/einBW;
  int npheBin = (nphe - npheMin)/npheBW;

  if(pid == -11) bvp_pos[einBin][npheBin]->Fill(p, beta);
  else if(pid == 211) bvp_pi[einBin][npheBin]->Fill(p, beta);
  else if(pid == 321) bvp_k[einBin][npheBin]->Fill(p, beta);
  else if(pid == 2212) bvp_prot[einBin][npheBin]->Fill(p, beta);
  bvp[einBin][npheBin]->Fill(p, beta);
}
infile.close();

TCanvas *can_pos = new TCanvas("can_pos", "can_pos", 30, 30, 1200, 800);
can_pos->Divide(nEinBins, nNpheBins, 0.0001, 0.0001);

TCanvas *can_pi = new TCanvas("can_pi", "can_pi", 50, 50, 1200, 800);
can_pi->Divide(nEinBins, nNpheBins, 0.0001, 0.0001);

TCanvas *can_k = new TCanvas("can_k", "can_k", 70, 70, 1200, 800);
can_k->Divide(nEinBins, nNpheBins, 0.0001, 0.0001);

TCanvas *can_prot = new TCanvas("can_prot", "can_prot", 90, 90, 1200, 800);
can_prot->Divide(nEinBins, nNpheBins, 0.0001, 0.0001);

TCanvas *can = new TCanvas("can", "can", 110, 110, 1200, 800);
can->Divide(nEinBins, nNpheBins, 0.0001, 0.0001);

int padCount = 1;
for(int n = nNpheBins-1; n >= 0; n--) {
  for(int e = 0; e < nEinBins; e++) {

    can_pos->cd(padCount);
    can_pos->cd(padCount)->SetLogz();
    bvp_pos[e][n]->Draw("colz");

    can_pi->cd(padCount);
    can_pi->cd(padCount)->SetLogz();
    bvp_pi[e][n]->Draw("colz");

    can_k->cd(padCount);
    can_k->cd(padCount)->SetLogz();
    bvp_k[e][n]->Draw("colz");

    can_prot->cd(padCount);
    can_prot->cd(padCount)->SetLogz();
    bvp_prot[e][n]->Draw("colz");

    can->cd(padCount);
    can->cd(padCount)->SetLogz();
    bvp[e][n]->Draw("colz");

    padCount++;

}}



}
