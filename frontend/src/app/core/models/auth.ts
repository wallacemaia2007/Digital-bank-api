export type SigninCredentials = {
  email: string;
  password: string;
};

export type SigninCredentialsResponse = {
  token?: string;
  user?: User;
};


