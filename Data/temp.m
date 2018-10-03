v1 = load("v1.txt");
i1 = load('i1.txt');
m1 = load('m1.txt');


figure(1)
plot(i1);
ylabel('i1');
figure(2)
plot(m1)
ylabel('m1');
figure(3)
plot(v1)
ylabel('v1')



%% square and triangular wave generation
clear;clc;clf;
dt=0.01; T = 20;
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
dlmwrite('squareWave-20_0.01',M_square, ' ')
dlmwrite('TriangleWave-20_0.01',M_triangle, ' ')

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
