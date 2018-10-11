clear;clc;clf;

dataset = "triangle";
directory = "storedResults/" + dataset;
m = load(directory + "/memristance.txt");
v = load(directory + "/voltage.txt");
m = m';
v = v';
figure(1)
plot(m(:,1))
hold on
plot(m(:,2))
figure(2)
plot(m(:,2),m(:,1),'b');
hold on

dataset = "square";
directory = "storedResults/" + dataset;
m = load(directory + "/memristance.txt");
v = load(directory + "/voltage.txt");
m = m';
v = v';

figure(1)
plot(m(:,1))
plot(m(:,2))
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

%% Test of dataset
load('dataset.csv')

%% temp code 2

T = 10*(1/50);
Fs = 1000;
dt = 1/Fs;
t = 0:dt:T-dt;
x = sawtooth(2*pi*50*t,0.5);
plot(t,x)

plot(t,x)
grid on
