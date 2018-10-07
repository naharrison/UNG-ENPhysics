{

TF1 *pi = new TF1("pi", "sqrt(x*x/(0.14*0.14 + x*x))", 0.1, 5.2);

TF1 *pr = new TF1("pr", "sqrt(x*x/(0.938*0.938 + x*x))", 0.1, 5.2);

TF1 *k = new TF1("k", "sqrt(x*x/(0.494*0.494 +x*x))", 0.1, 5.2);

TF1 *e = new TF1("e", "sqrt(x*x/(0.0*0.0))", 0.1, 5.2); 

//TF1 *middle = new TF1("middle", "0.5*(sqrt(x*x/(0.14*0.14 + x*x)) + sqrt(x*x/(0.938*0.938 + x*x)))", 0.1, 5.2);
//middle->SetLineColor(kBlue);

TF1 *lowerpi = new TF1("lowerpi", ".75*sqrt(x*x/(0.14*0.14 + x*x)) + .25*sqrt(x*x/(0.494*0.494 + x*x))", 0.1, 5.2);
lowerpi->SetLineColor(kBlue);

TF1 *upperpi = new TF1("upperpi", "1.25*sqrt(x*x/(0.14*0.14 + x*x)) - .25*sqrt(x*x/(0.494*0.494 + x*x))", 0.1, 5.2); 
upperpi->SetLineColor(kBlue);

TF1 *upperpr = new TF1("upperpr",".75*sqrt(x*x/(.938*.938 + x*x)) + .25*sqrt(x*x/(0.494*0.494 + x*x))", 0.1, 5.2);
upperpr->SetLineColor(kBlue);


TF1 *lowerpr = new TF1("lowerpr", "1.25*sqrt(x*x/(0.938*0.938 + x*x)) - .25*sqrt(x*x/(0.494*0.494 + x*x))", 0.1, 5.2); 
lowerpr->SetLineColor(kBlue);

TF1 *lowerk = new TF1("lowerk", "1.25*sqrt(x*x/(0.494*0.494 + x*x)) - .25*sqrt(x*x/(0.14*0.14 + x*x))", 0.1, 5.2);
lowerk->SetLineColor(kBlue);

TF1 *upperk = new TF1("upperk", "0.75*sqrt(x*x/(0.494*0.494 + x*x)) + .25*sqrt(x*x/(0.14*0.1 4+ x*x))", 0.1, 5.2);
upperk->SetLineColor(kBlue);

TF1 *uppere = new TF1("uppere", "1.25*sqrt(x*x/(0.0*0.0 + x*x)) - .25*sqrt(x*x/(0.14*0.14 + x*x))", 0.1, 5.2);
uppere->SetLineColor(kBlue);

TF1 *lowere = new TF1("lowere", ".75*sqrt(x*x/(0.0*0.0 + x*x)) + .25*sqrt(x*x/(0.14*0.14 + x*x))", 0.1, 5.2);
lowere->SetLineColor(kBlue);

TCanvas *can = new TCanvas("can", "can", 10, 10, 700, 500);


pi->Draw();
pr->Draw("same");
//middle->Draw("same");
lowerpi->Draw("same");
upperpi->Draw("same");
upperpr->Draw("same");
lowerpr->Draw("same");
lowerk->Draw("same");
upperk->Draw("same");
uppere->Draw("same");
lowere->Draw("same");
}
