{

const int nEoutBins = 5;
float eoutMin = 0.0;
float eoutMax = 1.1;
float eoutBW = (eoutMax - eoutMin)/nEoutBins;
const int nThetaBins = 5;
float thetaMin = 0.088;
float thetaMax = 2.22;
float thetaBW = (thetaMax - thetaMin)/nThetaBins;

TH2F *bvp_pos[nEoutBins][nThetaBins];
TH2F *bvp_pi[nEoutBins][nThetaBins];
TH2F *bvp_k[nEoutBins][nThetaBins];
TH2F *bvp_prot[nEoutBins][nThetaBins];
TH2F *bvp[nEoutBins][nThetaBins];

for(int e = 0; e < nEoutBins; e++) {
  for(int t = 0; t < nThetaBins; t++) {

    bvp_pos[e][t] = new TH2F(Form("bvp_pos_e%i_t%i", e, t), Form("bvp_pos_e%i_t%i", e, t), 200, 0, 5, 200, 0.5, 1.2);
    bvp_pi[e][t] = new TH2F(Form("bvp_pi_e%i_t%i", e, t), Form("bvp_pi_e%i_t%i", e, t), 200, 0, 5, 200, 0.5, 1.2);
    bvp_k[e][t] = new TH2F(Form("bvp_k_e%i_t%i", e, t), Form("bvp_k_e%i_t%i", e, t), 200, 0, 5, 200, 0.5, 1.2);
    bvp_prot[e][t] = new TH2F(Form("bvp_prot_e%i_t%i", e, t), Form("bvp_prot_e%i_t%i", e, t), 200, 0, 5, 200, 0.5, 1.2);
    bvp[e][t] = new TH2F(Form("bvp_e%i_t%i", e, t), Form("bvp_e%i_t%i", e, t), 200, 0, 5, 200, 0.5, 1.2);

}}

ifstream infile("/Users/naharrison/master/data-samples/e1f/Pid-Data/tmp-649951.txt");
for(int k = 0; k < 649951; k++) {
  int pid;
  float p, theta, beta, nphe, ein, eout;
  infile >> pid >> p >> theta >> beta >> nphe >> ein >> eout;

  int eoutBin = (eout - eoutMin)/eoutBW;
  int thetaBin = (theta - thetaMin)/thetaBW;

  if(pid == -11) bvp_pos[eoutBin][thetaBin]->Fill(p, beta);
  else if(pid == 211) bvp_pi[eoutBin][thetaBin]->Fill(p, beta);
  else if(pid == 321) bvp_k[eoutBin][thetaBin]->Fill(p, beta);
  else if(pid == 2212) bvp_prot[eoutBin][thetaBin]->Fill(p, beta);
  bvp[eoutBin][thetaBin]->Fill(p, beta);
}
infile.close();

TCanvas *can_pos = new TCanvas("can_pos", "can_pos", 30, 30, 1200, 800);
can_pos->Divide(nEoutBins, nThetaBins, 0.0001, 0.0001);

TCanvas *can_pi = new TCanvas("can_pi", "can_pi", 50, 50, 1200, 800);
can_pi->Divide(nEoutBins, nThetaBins, 0.0001, 0.0001);

TCanvas *can_k = new TCanvas("can_k", "can_k", 70, 70, 1200, 800);
can_k->Divide(nEoutBins, nThetaBins, 0.0001, 0.0001);

TCanvas *can_prot = new TCanvas("can_prot", "can_prot", 90, 90, 1200, 800);
can_prot->Divide(nEoutBins, nThetaBins, 0.0001, 0.0001);

TCanvas *can = new TCanvas("can", "can", 110, 110, 1200, 800);
can->Divide(nEoutBins, nThetaBins, 0.0001, 0.0001);

int padCount = 1;
for(int n = nThetaBins-1; n >= 0; n--) {
  for(int e = 0; e < nEoutBins; e++) {

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
