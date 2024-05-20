export interface IDatasetTherapy {
  id?: number;
  datasetTheraphyId?: number;
  therapy?: string;
  datasetIdId?: number;
}

export class DatasetTherapy implements IDatasetTherapy {
  constructor(
    public id?: number,
    public datasetTheraphyId?: number,
    public therapy?: string,
    public datasetIdId?: number,
  ) {}
}
