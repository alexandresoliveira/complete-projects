import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  color: #757575;

  > a {
    align-self: flex-start;
    text-decoration: none;
    font-weight: bold;
    font-size: 18px;
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 24px;
    color: #757575;

    svg {
      margin-right: 8px;
      color: #757575;
    }
  }
`;

export const Title = styled.h1`
  font-weight: bold;
  font-family: 'Dancing Script', cursive;
  font-size: 48px;
  color: #757575;
`;

export const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-top: 24px;
  color: #757575;

  h1 {
    font-family: 'Dancing Script', cursive;
    font-size: 36px;
  }

  form {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    margin: 24px 0px;

    div {
      width: 100%;
    }

    button {
      width: 100%;
    }
  }
`;

export const RowButtons = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  padding: 24px 0px;

  button {
    width: 220px;
  }

  @media (max-width: 512px) {
    button {
      width: 100%;
    }
  }
`;
