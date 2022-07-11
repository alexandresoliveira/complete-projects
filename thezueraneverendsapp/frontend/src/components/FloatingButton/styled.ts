import styled from 'styled-components';
import { shade } from 'polished';

interface ContainerProps {
  primaryColor?: string;
}

export const Container = styled.button<ContainerProps>`
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: ${props => (props.primaryColor ? props.primaryColor : '#ee551e')};
  position: absolute;
  bottom: 0px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 36px 0px;
  border: 0px;
  transition: box-shadow 0.2s, background-color 0.2s;
  box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.3);

  &:hover {
    box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.3);
    background-color: ${props =>
      shade(-0.3, props.primaryColor ? props.primaryColor : '#ee551e')};
  }

  svg {
    font-size: 24px;
  }
`;
