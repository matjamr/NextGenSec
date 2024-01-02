export interface UserPlaceAssigment {
  id: number,
  user: {
    id: number,
    email: string
  },
  assignmentRole: string
}
