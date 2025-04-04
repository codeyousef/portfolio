export enum UserRole {
  ADMIN = 'ADMIN',
  CONTRIBUTOR = 'CONTRIBUTOR',
  USER = 'USER'
}

export interface User {
  id: string;
  username: string;
  password: string; // Only used for input forms, never store this
  name: string;
  email: string;
  role: UserRole;
  createdAt: string;
  updatedAt: string;
  lastLogin: string | null;
  isAccountActive: boolean;
}