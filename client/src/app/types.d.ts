export type database_entity = {
  id: number;
  to: string;
  from: string;
  gas: string;
  gasPrice: string;
  blockNumber: string;
  transactionHash: string;
  date: string;
};

export type transaction_listener_state = {
  enabled: boolean;
};

export type simple_search = {
  searchBy: string;
  value: string;
  page: number;
};

export type complex_search = {
  page: number;
  to: string?;
  from: string?;
  gas: string?;
  gasPrice: string?;
  transactionHash: string?;
  date: string?;
};

export type page<T> = {
  collection: T[];
  currentPage: number;
  totalPages: number;
};
