clear;clc;clf;
pause_time = 2;



dir = "preset/series/";
dataset = "triangle";
m = load(dir + dataset + "/memristance.txt"); m = m';
figure(1); plot(m(:,1),m(:,2)); hold on;

pause(pause_time)



%% optimization of readout vector
clear;clc;
dir = "preset/series/";
dataset = "square";
X_s = load(dir + dataset + "/memristance.txt");
dataset = "triangle";
X_t = load(dir + dataset + "/memristance.txt");
theta_s=zeros(1,length(X_s(:,1)));
theta_t=zeros(1,length(X_t(:,1)));

m = length(X_s(1,:));
nbrInternalStates = length(X_s(:,1));

y = ones(1,m);
for j = 1:nbrInternalStates
    for i = 1:m
        delta_theta_s_j = 1/m * ( (theta_s*X_s(:,i)-y(i)) ) * X_s(j,i);
        theta_s(j) = theta_s(j) - 0.1 * delta_theta_s_j;
    end
end

y = -1 * ones(1,m);
for j = 1:nbrInternalStates
    for i = 1:m
        delta_theta_t_j = 1/m * ( (theta_t*X_t(:,i)-y(i)) ) * X_t(j,i);
        theta_t(j) = theta_t(j) - 0.1 * delta_theta_t_j;
    end
end
    
plot(theta_s*X_s,'b'); hold on
plot(theta_s*X_t,'b'); 

plot(theta_t*X_s,'g');
plot(theta_t*X_t,'g'); hold off

plot(theta_s*X_s); hold on
plot(theta_t*X_s);

%% read combined
clear;clc;clf;
pause_time = 0.5;

dir = "preset/series/";
dataset = "triangle";
m = load(dir + dataset + "/triangle.txt"); m = m';
figure(1); plot(m(2,:))



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



%% plot
clc;
d = load("combined/combined_4/combined_4.txt");
plot(d(:,2));
