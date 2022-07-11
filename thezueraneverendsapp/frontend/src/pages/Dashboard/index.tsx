import React from 'react';
import { FiPlus } from 'react-icons/fi';

import { Container, CardInfo } from './styled';

import FloatingButton from '../../components/FloatingButton';

const Dashboard: React.FC = () => {
  return (
    <Container>
      <CardInfo>
        <p>Número de frases</p>
        <span>5</span>
      </CardInfo>
      <CardInfo>
        <p>Aprovações pendentes</p>
        <span>5</span>
      </CardInfo>
      <FloatingButton icon={FiPlus} />
    </Container>
  );
};

export default Dashboard;
