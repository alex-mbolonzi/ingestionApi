import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 11282,
  login: 'aEV@YV',
};

export const sampleWithPartialData: IUser = {
  id: 1932,
  login: '{@DYNQER\\ A8hYAr\\<2\\Nws\\Hs\\#G9MgX',
};

export const sampleWithFullData: IUser = {
  id: 6194,
  login: 'kL2Q@DeS\\+NBQbv\\RQAR\\DrV',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
