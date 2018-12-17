function p = plotDotProductOfRandomSong(m,theta_blues,theta_classical, nbrOfPlots, y_blues, y_classical)
    nbrOfSongs = size(m,3);
    for i = 1 : nbrOfPlots
        iRandomSong = round(rand * (nbrOfSongs - 1)) + 1;
        plot(abs( theta_blues * m(:,:,iRandomSong) - y_blues),'r');
        hold on
        plot(abs( theta_classical * m(:,:,iRandomSong) - y_classical),'b');
        legend('blues dot product','classical dot product')
    end
end