import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { DatasetTechAndAssayDetailComponent } from 'app/entities/dataset-tech-and-assay/dataset-tech-and-assay-detail.component';
import { DatasetTechAndAssay } from 'app/shared/model/dataset-tech-and-assay.model';

describe('Component Tests', () => {
  describe('DatasetTechAndAssay Management Detail Component', () => {
    let comp: DatasetTechAndAssayDetailComponent;
    let fixture: ComponentFixture<DatasetTechAndAssayDetailComponent>;
    const route = { data: of({ datasetTechAndAssay: new DatasetTechAndAssay(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [DatasetTechAndAssayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatasetTechAndAssayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatasetTechAndAssayDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datasetTechAndAssay).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
