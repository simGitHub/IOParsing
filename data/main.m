%% testing
clc;
v = load ('voltage_threeColumnTest.txt');
v = v';
data = load('threeColumnTest.txt');


%%
clear;clc;clf;
pause_time = 2;



dir = "preset/series/";
m = load(dir + "/memristance_square.txt"); m = m';
t = linspace(0,19,length(m(:,1)));
figure(1); plot(t,m(:,1)); hold on;

pause(pause_time)



%% calculation of readout vector
clc;clf;
dir ="preset/series/";
X_s = load(dir + "/memristance_square.txt");
X_t = load(dir + "/memristance_triangle.txt");
theta_s=zeros(1,length(X_s(:,1)));
theta_t=zeros(1,length(X_t(:,1)));
unknown_label = X_s;

m = length(X_s(1,:));
nbrInternalStates = length(X_s(:,1));

y = ones(1,m);
for j = 1:nbrInternalStates
    for i = 1:m
        delta_theta_s_j = 1/m * ( (theta_s*X_s(:,i)-y(i)) ) * X_s(j,i);
        theta_s(j) = theta_s(j) - 0.1 * delta_theta_s_j;
    end
end
y =  - ones(1,m);
for j = 1:nbrInternalStates
    for i = 1:m
        delta_theta_t_j = 1/m * ( (theta_t*X_t(:,i)-y(i)) ) * X_t(j,i);
        theta_t(j) = theta_t(j) - 0.1 * delta_theta_t_j;
    end
end
    
figure(1)
plot(theta_s*X_s,'b'); hold on
plot(theta_t*X_t,'g'); 
title('square on thetaSquare and same for triangle')
legend('square output on square signal' , 'triangle output on triangle signal')
xlabel('t')
hold off


figure(2)
plot(theta_t*unknown_label,'b'); hold on
plot(theta_s*unknown_label,'g');
title('Unknown input on each output vectors')
hold off

classification = zeros(m,1);
for i = 1:m
    output_t = theta_t*unknown_label;
    output_s = theta_s*unknown_label;
    if(abs(output_s(i) - 1) < abs(output_t(i) + 1))
        classification(i) = 1;
    else
        classification(i) = 0;
    end
end
figure(3)
plot(classification)
title('classification of output, 1 is square, -1 is triangle')

        
%% classification with learnt output vector
clc;clf;clear;
theta_s = [0.197716682342451,0.0347564827602104,0.00611428041567514,0.00100506538404866,0.000143868955011865,7.89704705169010e-05,-4.85361178273661e-06,1.79267768542634e-05];
theta_t = [-0.199367635073782,-0.0402634865879282,-0.00809402913358132,-0.00163919929123000,-0.000320362192202665,-9.09260860525190e-05,-9.64678418545205e-06,-1.07283916297108e-05];
dir = "preset/series/";
X_s = load(dir + "/memristance_square.txt");
X_t = load(dir + "/memristance_triangle.txt");

m = length(X_s(1,:));
nbrInternalStates = length(X_s(:,1));
    
figure(1)
plot(theta_s*X_s,'b'); hold on
plot(theta_t*X_t,'g'); 
title('square on thetaSquare and same for triangle')
legend('square output on square signal' , 'triangle output on triangle signal')
xlabel('t')
hold off

unknown_label = X_s;
figure(2)
plot(theta_t*unknown_label,'b'); hold on
plot(theta_s*unknown_label,'g');
title('Unknown input on each output vectors')
hold off

classification = zeros(m,1);
for i = 1:m
    output_t = theta_t*unknown_label;
    output_s = theta_s*unknown_label;
    if(abs(output_s(i) - 1) < abs(output_t(i) + 1))
        classification(i) = 1;
    else
        classification(i) = -1;
    end
end
figure(3)
plot(classification)
title('classification of output, 1 is square, -1 is triangle prediction')



%% modified triangular wave generation
clear;clc;clf;
dt=0.01; T = 30;
t = 0:dt:T;
omega = 16;
amp = 1;
triangle = amp * sawtooth(t*omega,0.5);

% delay the signal
delay = 0.8 * 100;
triangle_delay = triangle(delay:length(triangle));
t_delay = t(1:length(t)-delay+1);

plot(t,triangle)

M = zeros(length(t),2);
M(:,2) = triangle;
M(:,1) = t;
dataName = "triangle_16";
dlmwrite("/users/simon/eclipse-workspace/simulator/src/data/par/triangle/" + dataName + "/" + dataName + ".txt",M, ' ')
disp('Done')
%% read triangle voltage data
d1 = load("/Users/simon/eclipse-workspace/simulator/src/data/triangle/modified/triangle_2/triangle_2.txt");
d2 = load("/Users/simon/eclipse-workspace/simulator/src/data/triangle/modified/triangle_4/triangle_4.txt");
d3 = load("/Users/simon/eclipse-workspace/simulator/src/data/triangle/modified/triangle_2_delay/triangle_2_delay.txt");
d4 = load("/Users/simon/eclipse-workspace/simulator/src/data/triangle/modified/triangle_4_delay/triangle_4_delay.txt");
plot(d1(:,2)); hold on;
plot(d2(:,2)); plot(d3(:,2)); plot(d4(:,2)); hold off;


%% modified square wave generator

clear;clc;clf;
dt=0.01; T = 30;
t = 0:dt:T;
omega = 2;
amp = 1;
square = amp * square(t*omega);

% delay the signal
delay = 0.8 * 100;
square_delay = square(delay:length(square));
t_delay = t(1:length(t)-delay+1);

plot(t,square)
M = zeros(length(t),2);
M(:,2) = square;
M(:,1) = t;
dataName = "square_2_amp09";
dlmwrite("/users/simon/eclipse-workspace/simulator/src/data/par/square/" + dataName + "/" + dataName + ".txt",M, ' ')

%% merged square and triangle wave generator
clear;clc;clf;
dt=0.01; 
omega = 8; amp = 1;
T_square = 9; T_triangle = 9.5;
t_square = 0:dt:T_square - dt;
t_triangle = 0:dt:T_triangle - dt;
t=0:dt:2*T_square + T_triangle -dt;

squareWave = square(t_square * omega);
triangleWave = sawtooth(t_triangle * omega, 1/2);


% plot(t_square,squareWave); hold on;
% plot(t_triangle, triangleWave); hold off;
nbrIndex_square = length(t_square);
nbrIndex_triangle = length(t_triangle);
signal = zeros(1,nbrIndex_square * 2 + nbrIndex_triangle);
signal(1:nbrIndex_square) = squareWave;
signal(nbrIndex_square + 1 : nbrIndex_triangle + nbrIndex_square) = triangleWave;
signal(nbrIndex_square + nbrIndex_triangle + 1: 2*nbrIndex_square + nbrIndex_triangle) = squareWave;
figure(2); plot(t,signal);

M = zeros(length(t),2);
M(:,2) = signal;
M(:,1) = t;
dataName = "combined_8";
dlmwrite("/users/simon/eclipse-workspace/simulator/src/data/par/combined/" + dataName + "/" + dataName + ".txt",M, ' ')


