%2a
Mu=[15 25];
Covariance=[2 0;0 2.5];
%sigma=Covariance*transpose(Covariance);
data=mvnrnd(Mu,Covariance,1500);
[mu, covariance] = ecmnmle(data);
disp(Mu);
disp(mu);
disp(Covariance);
disp(covariance);


