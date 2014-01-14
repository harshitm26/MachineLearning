%3b
Mu=[75;40;5];
Covariance = cat(3,5,2.5,1);
obj=gmdistribution(Mu,Covariance,[0.7 0.2 0.1]);
a=zeros(10000,1);
for i=1:10000,
    a(i)=random(obj);
end
obj1=gmdistribution.fit(a,3);
disp(obj1);
disp(obj1.mu);
disp(obj1.Sigma);