function [m_trainingSet, m_testSet] = splitData(m, splitConstant)
    nbrOfSongs = size(m,3);
    splitValue = int8(nbrOfSongs * splitConstant);
    m_trainingSet = m(:,:,1:splitValue);
    m_testSet = m(:,:,splitValue + 1 : nbrOfSongs);
end