function theta = trainReadOutVector(genre, m, y, transient)
    nbrOfMemristors = size(m,1);
    nbrOfTimeSteps = size(m,2);
    nbrOfTrainingSongs = size(m,3);
    theta = ones(1,nbrOfMemristors);
    eta = 0.001;
    transientValue = round(nbrOfTimeSteps * transient);
    gradient = zeros(nbrOfMemristors, nbrOfTrainingSongs);
    
    for iMem = 1:nbrOfMemristors
        for iSong = 1 : nbrOfTrainingSongs 
            for iTimeStep = (transientValue + 1):nbrOfTimeSteps
                thetaGradient_i = ( (theta * m(:,iTimeStep,iSong) - y) ) * m(iMem,iTimeStep,iSong);
                %disp(eta * thetaGradient_i)
                theta(iMem) = theta(iMem) - eta * thetaGradient_i;
            end
            gradient(iMem, iSong) = thetaGradient_i;
        end
    end
    disp("Calculation of readOutVector finished for genre type " + genre)
    fprintf("\n")
    %plot(gradient(1,:))
end