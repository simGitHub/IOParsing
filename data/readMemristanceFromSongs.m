function m = readMemristanceFromSongs(genre, directory, nbrOfMemristors, nbrOfTimeSteps, nbrOfSongs)
    m = zeros(nbrOfMemristors,nbrOfTimeSteps,nbrOfSongs);   
    for i = 0 : nbrOfSongs - 1
        if mod(i,10) == 0
            disp("Reading memristance_" + genre + ".0000" + int2str(i) + ".txt")
        end
        if i < 10
            m(:,:,i + 1) = load(directory + "/memristance_" + genre + ".0000" + int2str(i) + ".txt");
        else
            m(:,:,i + 1) = load(directory + "/memristance_" + genre + ".000" + int2str(i) + ".txt");
        end
    end
    disp("done reading memristance of song type " + genre)
end
