function theta = trainReadOutVector(genre, m, y, transient, eta, plot_theta_bool, plot_error_bool)
    nbrOfMemristors = size(m,1);
    nbrOfTimeSteps = size(m,2);
    nbrOfTrainingSongs = size(m,3);
    theta = ones(1,nbrOfMemristors);
    transientValue = round(nbrOfTimeSteps * transient);
    gradient_plot = zeros(nbrOfMemristors, round((1 - transient) * nbrOfTrainingSongs * nbrOfTimeSteps));
    theta_plot= zeros(nbrOfMemristors, round((1 - transient) * nbrOfTrainingSongs * nbrOfTimeSteps));
    error_plot = zeros(1,nbrOfTrainingSongs);
    
    counter = 0;
    sumCounter = 1;
    for iSong = 1 : nbrOfTrainingSongs 
        for iTimeStep = (transientValue + 1):nbrOfTimeSteps
            counter = counter + 1;
            for iMem = 1:nbrOfMemristors
                thetaGradient_i = ( (theta * m(:,iTimeStep,iSong) - y) ) * m(iMem,iTimeStep,iSong);
                theta(iMem) = theta(iMem) - eta * thetaGradient_i;
                gradient_plot(iMem,counter) = thetaGradient_i;
                theta_plot(iMem, counter) = theta(iMem);
            end
        end
        if plot_error_bool
            for iSum = 1:nbrOfTrainingSongs
                for jSum = (transientValue + 1):nbrOfTimeSteps
                    error_plot(sumCounter) = error_plot(sumCounter) + (theta * m(:,jSum,iSum) - y)^2;
                end
            end
            sumCounter = sumCounter + 1;
        end
    end
    
    error_plot = error_plot / (nbrOfTrainingSongs * (1 - transient) * nbrOfTimeSteps);
    
    if plot_theta_bool
        for iMem = 1:nbrOfMemristors
            if genre == "blues"
                figure(1)
                plot(theta_plot(iMem,:));
                hold on
            else
                figure(2)
                plot(theta_plot(iMem,:));
                hold on
            end
        end
    end
    if plot_error_bool
        if genre == "blues"
            figure(1)
            plot(error_plot / 10)
            ylim([0 1])
        else
            figure(2)
            plot(error_plot / 10)
            ylim([0 1])
        end
    end
        
    disp("Calculation of readOutVector finished for genre type " + genre)
    fprintf("\n")
    f = 0;
end