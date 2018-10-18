clear;clc;clf;

dir = "square/";

dataset = "square_2";
m = load(dir + dataset + "/memristance.txt"); m = m';
figure(1); plot(m(:,2),m(:,1)); hold on;
pause(0.5)



dataset = "square_4";
m = load(dir + dataset + "/memristance.txt"); m = m';
figure(1); plot(m(:,2),m(:,1));
pause(0.5)

dir = "triangle/";
dataset = "triangle_2";
m = load(dir + dataset + "/memristance.txt"); m = m';
d = load(dir + dataset + "/" + dataset + ".txt"); figure(2); plot(d(:,2)); hold on;
figure(1); plot(m(:,2),m(:,1));
pause(0.5)

dataset = "triangle_4";
m = load(dir + dataset + "/memristance.txt"); m = m';
figure(1); plot(m(:,2),m(:,1));


dataset = "triangle_2_amped";
m = load(dir + dataset + "/memristance.txt"); m = m';
d = load(dir + dataset + "/" + dataset + ".txt"); figure(2); plot(d(:,2));
figure(1); plot(m(:,2),m(:,1));
pause(0.5)

dir = "square/";
dataset = "square_2_dim";
m = load(dir + dataset + "/memristance.txt"); m = m';
figure(1); plot(m(:,2),m(:,1)); hold off;
pause(0.5)


% figure(2); plot(m(:,1)); hold on; plot(m(:,2)); hold off; figure(3); plot(m2(:,1)); hold on; plot(m2(:,2)); hold off;
% figure(4); plot(data(:,2)) 
% figure(5); plot(data2(:,2))



%% modified triangular wave generation
clear;clc;clf;
dt=0.01; T = 30;
t = 0:dt:T;
omega = 2;
triangle = 2 * sawtooth(t*omega,0.5);

% delay the signal
delay = 0.8 * 100;
triangle_delay = triangle(delay:length(triangle));
t_delay = t(1:length(t)-delay+1);

plot(t,triangle)

M = zeros(length(t),2);
M(:,2) = triangle;
M(:,1) = t;
dataName = "triangle_2_amped";
dlmwrite("/users/simon/eclipse-workspace/simulator/src/data/triangle/modified/" + dataName + "/" + dataName + ".txt",M, ' ')
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
square = 0.5*square(t*omega);

% delay the signal
delay = 0.8 * 100;
square_delay = square(delay:length(square));
t_delay = t(1:length(t)-delay+1);

plot(t,square)
M = zeros(length(t),2);
M(:,2) = square;
M(:,1) = t;
dataName = "square_2_dim";
dlmwrite("/users/simon/eclipse-workspace/simulator/src/data/square/" + dataName + "/" + dataName + ".txt",M, ' ')
