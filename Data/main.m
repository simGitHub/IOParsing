clear;clc;clf;

dataset = "square_1/";
directory = "storedResults/square/modified/frequency/" + dataset;
m = load(directory + "memristance.txt");
v = load(directory + "voltage.txt");
m = m';
v = v';
figure(1)
plot(m(:,1),'b')
hold on
plot(m(:,2),'b')
figure(2)
plot(m(:,2),m(:,1),'b');
hold on

dataset = "square_2/";
directory = "storedResults/square/modified/frequency/" + dataset;
m = load(directory + "/memristance.txt");
v = load(directory + "/voltage.txt");
m = m';
v = v';

figure(1)
plot(m(:,1),'r')
plot(m(:,2),'r')
hold off
figure(2)
plot(m(:,2),m(:,1),'r');
hold off




%% test analysis of data

clear;clc;clf;
hold on

dataset = "triangle";
directory = "storedResults/" + dataset;
m = load(directory + "/memristance.txt");
v = load(directory + "/voltage.txt");
m = m';
v = v';
Time = length(m) / 100;
delta_Time = 4;
m_new(:,:) = m(1:(delta_Time * 100),:);

figure(1)
plot(m_new(:,1));
hold on
plot(m_new(:,2));
figure(2)
plot(m_new(:,2),m_new(:,1),'b');
hold off


%% square and triangular wave generation
clear;clc;clf;
dt=0.01; T = 30;
t = 0:dt:T;
square = square(t*4);
triangle = sawtooth(t*4,0.5);

hold on
plot(t,square)
plot(t,triangle)
hold off

M_square = zeros(length(t),2);
M_triangle= zeros(length(t),2);
M_square(:,2) = square;
M_triangle(:,2) = triangle;
M_square(:,1) = t;
M_triangle(:,1) = t;
dlmwrite('square.txt',M_square, ' ')
dlmwrite('triangle.txt',M_triangle, ' ')


%% modified square wave generator

clear;clc;clf;
dt=0.01; T = 30;
t = 0:dt:T;
omega = 2;
square = square(t*omega);
delay = 0.8 * 100;
square_mod = square(delay:length(square));
t_mod = t(1:length(t)-delay+1);

invert = 1;
square_mod = invert * square_mod;
hold on
plot(t_mod,square_mod)
hold off

M_square = zeros(length(t_mod),2);
M_square(:,2) = square_mod;
M_square(:,1) = t_mod;
dlmwrite('/users/simon/eclipse-workspace/simulator/src/data/datasets/modifiedSquare/timeDelay/squareOrigin_delay.txt',M_square, ' ')


%% Test of dataset
load('dataset.csv')

%% plot code
clc;clf;clear;

square = load('square_1.txt');

plot(square(:,2))
