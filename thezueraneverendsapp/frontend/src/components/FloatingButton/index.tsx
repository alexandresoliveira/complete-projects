import React, { ButtonHTMLAttributes } from 'react';
import { IconBaseProps } from 'react-icons';

import { Container } from './styled';

interface FloatingButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  icon?: React.ComponentType<IconBaseProps>;
  primaryColor?: string;
}

const FloatingButton: React.FC<FloatingButtonProps> = ({
  icon: Icon,
  primaryColor,
  ...rest
}) => {
  return <Container primaryColor={primaryColor}>{Icon && <Icon />}</Container>;
};

export default FloatingButton;
