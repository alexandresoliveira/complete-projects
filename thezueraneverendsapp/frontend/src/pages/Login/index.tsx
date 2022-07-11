import React, { useCallback, useRef } from 'react';
import { FiMail, FiLock } from 'react-icons/fi';
import { Form } from '@unform/web';
import * as Yup from 'yup';
import { useHistory } from 'react-router-dom';

import { FormHandles } from '@unform/core';
import { Container, Content } from './styled';
import { useAuth } from '../../hooks/auth';

import Input from '../../components/Input';
import Button from '../../components/Button';
import Link from '../../components/Link';
import getValidationErrors from '../../utils/getValidationErrors';

import logo from '../../assets/logo.svg';

interface SignInFormData {
  email: string;
  password: string;
}

const Login: React.FC = () => {
  const formRef = useRef<FormHandles>(null);
  const { signIn } = useAuth();
  const history = useHistory();

  const handleSubmit = useCallback(
    async (data: SignInFormData) => {
      try {
        formRef.current?.setErrors({});

        const schema = Yup.object().shape({
          email: Yup.string()
            .required('Email is required')
            .email('Please, put a valid email'),
          password: Yup.string().min(6, 'Minimun 6 digits'),
        });

        await schema.validate(data, {
          abortEarly: false,
        });

        signIn({
          email: data.email,
          password: data.password,
        });

        history.push('/dashboard');
      } catch (err) {
        if (err instanceof Yup.ValidationError) {
          const errors = getValidationErrors(err);
          formRef.current?.setErrors(errors);
        }
      }
    },
    [signIn, history],
  );

  return (
    <>
      <Container>
        <Content>
          <img src={logo} alt="Logo" />
          <Form ref={formRef} onSubmit={handleSubmit}>
            <Input name="email" icon={FiMail} placeholder="Email" />
            <Input
              name="password"
              icon={FiLock}
              placeholder="Password"
              type="password"
            />
            <Button type="submit">Sign In</Button>
            <Link to="/users/new">Create New Account</Link>
          </Form>
        </Content>
      </Container>
    </>
  );
};

export default Login;
