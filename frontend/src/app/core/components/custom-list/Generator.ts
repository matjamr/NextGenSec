export interface Generator<T> {
  generate: (data: T) => string
}
