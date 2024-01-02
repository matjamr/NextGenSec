export interface UserPlaceAssigment {
  id: number,
  user: {
    id: number,
    email: string
  },
  assigmentRole: ROLE
}

export enum ROLE {
  USER = "USER",
  ADMiN = "ADMIN"
}
