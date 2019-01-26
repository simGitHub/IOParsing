function theta = trainReadOutVector_v2(genre, m, y, transient, eta, plot_theta_bool, plot_error_bool, useAverageTheta_bool, eta_momentum, nbrOfUpdates)
    nbrOfMemristors = size(m,1);
    nbrOfTimeSteps = size(m,2);
    nbrOfTrainingSongs = size(m,3);
    theta = ones(1,nbrOfMemristors);
    theta_new = ones(1,nbrOfMemristors);
    nbrErrorSums = 100;
    %ratio = nbrOfUpdates * nbrErrorSums / nbrOfTimeSteps / nbrOfTrainingSongs
    theta_plot = zeros(nbrOfMemristors, nbrOfUpdates + 1);
    theta_plot(:,1) = theta;
    for i_update = 1:nbrOfUpdates
        eta = eta * eta_momentum;
        for iMem = 1:nbrOfMemristors
            thetaGradient_i = 0;
            for i_sum_delta = 1:nbrErrorSums
                iT = round(rand()*(nbrOfTimeSteps - 1)) + 1;
                iS = round(rand()*(nbrOfTrainingSongs - 1)) + 1;
                thetaGradient_i = ( (theta * m(:,iT,iS) - y) ) * m(iMem,iT,iS) / nbrErrorSums + thetaGradient_i;
            end
            theta_new(iMem) = theta(iMem) - eta * thetaGradient_i;
            theta_plot(iMem, i_update + 1) = theta_new(iMem);
        end
        theta = theta_new;
    end
    if plot_theta_bool
        hold on
        for iMem = 1:nbrOfMemristors
            plot(theta_plot(iMem,:))
        end
        hold off
    end

end
        
      