TH1F *hh = new TH1F("hh" , "hh" , 100, -5, 5);
hh->FillRandom("gaus", 5000);

hh->Draw();
cout<<hh->GetRMS()<<endl;
