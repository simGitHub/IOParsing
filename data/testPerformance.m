function p = testPerformance(genre, m_test, theta_blues, theta_classical, transient)
    dataTestSize = size(m_test,3);
    nbrOfTimeSteps = size(m_test,2);
    transientValue = round(transient * nbrOfTimeSteps);
    nbrOfTests = round(dataTestSize * ( 1 - transient ) * nbrOfTimeSteps);
    cError_blues = 0;
    cError_classical = 0;
    for iSong = 1 : dataTestSize
        for iTimeStep = (transientValue + 1 ) : nbrOfTimeSteps
            if( abs(theta_blues * m_test(:,iTimeStep, iSong) - 1) < abs(theta_classical * m_test(:,iTimeStep, iSong) - 0) )
                cError_classical = cError_classical + 1;
            else
                cError_blues = cError_blues + 1;
            end
        end
        
    end
    cError_classical = cError_classical / nbrOfTests;
    cError_blues = cError_blues / nbrOfTests;
    disp("cError_blues: " + num2str(cError_blues))
    disp("cError_classical: " + num2str(cError_classical))
    disp("genre type tested: " + genre)
    fprintf("\n")
    
    p = 0;
end