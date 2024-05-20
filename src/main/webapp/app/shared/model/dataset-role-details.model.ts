export interface IDatasetRoleDetails {
  id?: number;
  datasetRoleDetailsId?: number;
  role?: string;
  email?: string;
  mudid?: string;
  name?: string;
  datasetIdId?: number;
}

export class DatasetRoleDetails implements IDatasetRoleDetails {
  constructor(
    public id?: number,
    public datasetRoleDetailsId?: number,
    public role?: string,
    public email?: string,
    public mudid?: string,
    public name?: string,
    public datasetIdId?: number,
  ) {}
}
