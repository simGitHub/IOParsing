function p = testPerformance(genre, m_test, theta_blues, theta_classical, transient, y_blues, y_classical)
    dataTestSize = size(m_test,3);
    nbrOfTimeSteps = size(m_test,2);
    transientValue = transient * nbrOfTimeSteps;
    nbrOfTests = dataTestSize * ( 1 - transient ) * nbrOfTimeSteps;
    cError_blues = 0;
    cError_classical = 0;
    accuracy_classical = 0;
    accuracy_blues = 0;
    cError_classical_iSong = 0;
    cError_blues_iSong = 0;
    
    for iSong = 1 : dataTestSize
        for iTimeStep = (transientValue + 1 ) : nbrOfTimeSteps
            if( abs(theta_blues * m_test(:,iTimeStep, iSong) - y_blues) < abs(theta_classical * m_test(:,iTimeStep, iSong) - y_classical) )
                cError_classical = cError_classical + 1;
                cError_classical_iSong = cError_classical_iSong + 1;
            else
                cError_blues = cError_blues + 1;
                cError_blues_iSong = cError_blues_iSong + 1;
            end
        end
        if cError_classical_iSong < cError_blues_iSong
            accuracy_classical = accuracy_classical + 1;
        else
            accuracy_blues = accuracy_blues + 1;
        end
        cError_classical_iSong = 0;
        cError_blues_iSong = 0;
    end
    cError_classical = cError_classical / nbrOfTests;
    cError_blues = cError_blues / nbrOfTests;
    %accuracy_classical = accuracy_classical / dataTestSize;
    %accuracy_blues = accuracy_blues / dataTestSize;
    display_bool = false;
    if genre == "blues"
        p = cError_blues; 
        if display_bool
            accuracy = 1 - cError_blues;
            disp("accuracy of classification for all timesteps: " + accuracy)
            disp("accuracy of classification per song: " + accuracy_blues / dataTestSize)
            disp("number of correct classified songs: " + accuracy_blues + " out of " + dataTestSize);
        end
    elseif genre == "classical"
        p = cError_classical; 
        if display_bool
            accuracy = 1 - cError_classical;
            disp("accuracy of classification for all timesteps: " + accuracy)
            disp("accuracy of classification per song: " + accuracy_classical / dataTestSize)
            disp("number of correct classified songs: " + accuracy_classical + " out of " + dataTestSize);
        end
    end
    if display_bool
        disp("genre type tested: " + genre)
    end
end