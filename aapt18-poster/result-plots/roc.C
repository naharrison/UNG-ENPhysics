{
gStyle->SetTitleFontSize(0.06);

// Kalanie
TGraph *kpos = new TGraph();
kpos->SetPoint(kpos->GetN(), 0.71770833, 0.89655172);
kpos->SetPoint(kpos->GetN(), 0.68385417, 0.89808482);
kpos->SetPoint(kpos->GetN(), 0.65104167, 0.89992801);
kpos->SetPoint(kpos->GetN(), 0.58958333, 0.89344909);
kpos->SetPoint(kpos->GetN(), 0.65364583, 0.84341398);
kpos->SetPoint(kpos->GetN(), 0.68072917, 0.81331674);
kpos->SetMarkerStyle(20);
kpos->SetMarkerColor(1);
kpos->SetLineColor(1);
kpos->SetLineWidth(2);


TGraph *kpi = new TGraph();
kpi->SetPoint(kpi->GetN(), 0.97749552, 0.96692077);
kpi->SetPoint(kpi->GetN(), 0.97487434, 0.96971998);
kpi->SetPoint(kpi->GetN(), 0.98234777, 0.96266381);
kpi->SetPoint(kpi->GetN(), 0.98052923, 0.96707059);
kpi->SetPoint(kpi->GetN(), 0.9800325, 0.96766006);
kpi->SetPoint(kpi->GetN(), 0.98070042, 0.96781269);
kpi->SetMarkerStyle(20);
kpi->SetMarkerColor(1);
kpi->SetLineColor(1);
kpi->SetLineWidth(2);


TGraph *kk = new TGraph();
kk->SetPoint(kk->GetN(), 0.62213307, 0.77227641);
kk->SetPoint(kk->GetN(), 0.6220662, 0.77638222);
kk->SetPoint(kk->GetN(), 0.56730191, 0.82412939);
kk->SetPoint(kk->GetN(), 0.60090271, 0.8089387);
kk->SetPoint(kk->GetN(), 0.60565028, 0.80521847);
kk->SetPoint(kk->GetN(), 0.60762287, 0.81152043);
kk->SetMarkerStyle(20);
kk->SetMarkerColor(1);
kk->SetLineColor(1);
kk->SetLineWidth(2);


TGraph *kprot = new TGraph();
kprot->SetPoint(kprot->GetN(), 0.98042339, 0.98328921);
kprot->SetPoint(kprot->GetN(), 0.97979312, 0.98453921);
kprot->SetPoint(kprot->GetN(), 0.98001467, 0.98451998);
kprot->SetPoint(kprot->GetN(), 0.98109186, 0.98135426);
kprot->SetPoint(kprot->GetN(), 0.98082065, 0.98198722);
kprot->SetPoint(kprot->GetN(), 0.98133251, 0.98240951);
kprot->SetMarkerStyle(20);
kprot->SetMarkerColor(1);
kprot->SetLineColor(1);
kprot->SetLineWidth(2);


// Cody
TGraph *cpos = new TGraph();
cpos->SetPoint(0, 0.0, 0.0);
cpos->SetMarkerStyle(21);
cpos->SetMarkerColor(2);
cpos->SetLineColor(2);
cpos->SetLineWidth(2);


TGraph *cpi = new TGraph();
cpi->SetPoint(cpi->GetN(), 0.96369, 0.917812);
cpi->SetPoint(cpi->GetN(), 0.957109, 0.91798);
cpi->SetPoint(cpi->GetN(), 0.95585, 0.91889);
cpi->SetPoint(cpi->GetN(), 0.959195, 0.931647);
cpi->SetMarkerStyle(21);
cpi->SetMarkerColor(2);
cpi->SetLineColor(2);
cpi->SetLineWidth(2);


TGraph *ck = new TGraph();
ck->SetPoint(ck->GetN(), 0.000216122,	0.21428);
ck->SetPoint(ck->GetN(), 7.204E-05,	0.03126);
ck->SetPoint(ck->GetN(), 0.0003602,	0.384615);
ck->SetPoint(ck->GetN(), 0.00028816, 0.0689655);
ck->SetMarkerStyle(21);
ck->SetMarkerColor(2);
ck->SetLineColor(2);
ck->SetLineWidth(2);


TGraph *cprot = new TGraph();
cprot->SetPoint(cprot->GetN(), 0.96733,	0.91756);
cprot->SetPoint(cprot->GetN(), 0.96723,	0.90888);
cprot->SetPoint(cprot->GetN(), 0.96238,	0.901383);
cprot->SetPoint(cprot->GetN(), 0.97924,	0.90512);
cprot->SetMarkerStyle(21);
cprot->SetMarkerColor(2);
cprot->SetLineColor(2);
cprot->SetLineWidth(2);


// Mireya
TGraph *mpos = new TGraph();
mpos->SetPoint(mpos->GetN(), 0.4890625, 0.7734761120263591);
mpos->SetPoint(mpos->GetN(), 0.1796875, 0.8801020408163265);
mpos->SetPoint(mpos->GetN(), 0.1796875, 0.8801020408163265);
mpos->SetMarkerStyle(22);
mpos->SetMarkerColor(4);
mpos->SetLineColor(4);
mpos->SetLineWidth(2);


TGraph *mpi = new TGraph();
mpi->SetPoint(mpi->GetN(), 0.943072273095931, 0.9801859192560897);
mpi->SetPoint(mpi->GetN(), 0.9435353283061441, 0.9784724364533798);
mpi->SetPoint(mpi->GetN(), 0.9810596387046803, 0.8816801305445493);
mpi->SetMarkerStyle(22);
mpi->SetMarkerColor(4);
mpi->SetLineColor(4);
mpi->SetLineWidth(2);


TGraph *mk = new TGraph();
mk->SetPoint(mk->GetN(), 0.762888665997994, 0.6813784042044911);
mk->SetPoint(mk->GetN(), 0.762888665997994, 0.6813784042044911);
mk->SetPoint(mk->GetN(), 0.2436308926780341, 0.861958836053939);
mk->SetMarkerStyle(22);
mk->SetMarkerColor(4);
mk->SetLineColor(4);
mk->SetLineWidth(2);


TGraph *mprot = new TGraph();
mprot->SetPoint(mprot->GetN(), 0.9595327588314387, 0.9830008374357249);
mprot->SetPoint(mprot->GetN(), 0.9595327588314387, 0.9830008374357249);
mprot->SetPoint(mprot->GetN(), 0.889637574868598, 0.9894176073001941);
mprot->SetMarkerStyle(22);
mprot->SetMarkerColor(4);
mprot->SetLineColor(4);
mprot->SetLineWidth(2);


TCanvas *can = new TCanvas("can", "can", 10, 10, 1400, 500);
can->Divide(4, 1, 0.0001, 0.0001);

can->cd(1);
kpos->SetTitle("positrons");
kpos->GetXaxis()->SetLimits(0.0, 1.0);
kpos->GetYaxis()->SetRangeUser(0.7, 1.0);
kpos->GetXaxis()->SetTitle("Efficiency");
kpos->GetXaxis()->SetTitleSize(0.055);
kpos->GetXaxis()->SetTitleOffset(0.8);
kpos->GetXaxis()->SetLabelSize(0.045);
kpos->GetYaxis()->SetTitle("Purity");
kpos->GetYaxis()->SetTitleSize(0.055);
kpos->GetYaxis()->SetTitleOffset(0.8);
kpos->GetYaxis()->SetLabelSize(0.045);
kpos->Draw("ALP");
cpos->Draw("LP");
mpos->Draw("LP");

auto legend = new TLegend(0.1, 0.65, 0.65, 0.9);
legend->AddEntry(kpos, "Probability", "lp");
legend->AddEntry(cpos, "Machine Learning", "lp");
legend->AddEntry(mpos, "Cut", "lp");
legend->Draw();

can->cd(2);
kpi->SetTitle("pions");
kpi->GetXaxis()->SetLimits(0.92, 1.0);
kpi->GetYaxis()->SetRangeUser(0.86, 1.0);
kpi->GetXaxis()->SetTitle("Efficiency");
kpi->GetXaxis()->SetNdivisions(505, 1);
kpi->GetXaxis()->SetTitleSize(0.055);
kpi->GetXaxis()->SetTitleOffset(0.8);
kpi->GetXaxis()->SetLabelSize(0.045);
kpi->GetYaxis()->SetTitle("Purity");
kpi->GetYaxis()->SetTitleSize(0.055);
kpi->GetYaxis()->SetTitleOffset(0.8);
kpi->GetYaxis()->SetLabelSize(0.045);
kpi->Draw("ALP");
cpi->Draw("LP");
mpi->Draw("LP");

can->cd(3);
kk->SetTitle("kaons");
kk->GetXaxis()->SetLimits(0.1, 1.0);
kk->GetYaxis()->SetRangeUser(0.6, 1.0);
kk->GetXaxis()->SetTitle("Efficiency");
kk->GetXaxis()->SetTitleSize(0.055);
kk->GetXaxis()->SetTitleOffset(0.8);
kk->GetXaxis()->SetLabelSize(0.045);
kk->GetYaxis()->SetTitle("Purity");
kk->GetYaxis()->SetTitleSize(0.055);
kk->GetYaxis()->SetTitleOffset(0.8);
kk->GetYaxis()->SetLabelSize(0.045);
kk->Draw("ALP");
ck->Draw("LP");
mk->Draw("LP");

can->cd(4);
kprot->SetTitle("protons");
kprot->GetXaxis()->SetLimits(0.85, 1.0);
kprot->GetYaxis()->SetRangeUser(0.88, 1.0);
kprot->GetXaxis()->SetTitle("Efficiency");
kprot->GetXaxis()->SetTitleSize(0.055);
kprot->GetXaxis()->SetTitleOffset(0.8);
kprot->GetXaxis()->SetLabelSize(0.045);
kprot->GetYaxis()->SetTitle("Purity");
kprot->GetYaxis()->SetTitleSize(0.055);
kprot->GetYaxis()->SetTitleOffset(0.8);
kprot->GetYaxis()->SetLabelSize(0.045);
kprot->Draw("ALP");
cprot->Draw("LP");
mprot->Draw("LP");




}
