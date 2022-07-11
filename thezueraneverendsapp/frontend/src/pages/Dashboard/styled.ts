import styled from 'styled-components';

interface CardProps {
  primaryColor?: string;
}

export const Container = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;

  @media (max-width: 600px) {
    flex-direction: column;
  }
`;

export const CardInfo = styled.div<CardProps>`
  display: flex;
  width: 100%;
  flex-direction: column;
  align-items: center;
  padding: 16px;
  margin: 6px 6px;
  border-radius: 12px;
  box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.1);
  background: ${props => (props.primaryColor ? props.primaryColor : '#e5e5e5')};

  p {
    font-size: 18px;
    padding: 12px;
    width: 100%;
    font-weight: lighter;
    text-transform: uppercase;
    color: #616161;
  }

  span {
    font-size: 72px;
    font-weight: bold;
    flex: 1;
  }
`;
